import Data.List (elemIndex)

-- Definición de Palet
data Palet = Pal String Int deriving (Eq, Show)

newP :: String -> Int -> Palet
newP = Pal  -- Construye un palet con destino y peso

destinationP :: Palet -> String
destinationP (Pal city _) = city  -- Devuelve la ciudad destino

netP :: Palet -> Int
netP (Pal _ weight) = weight  -- Devuelve el peso

-- Definición de Route
data Route = Rou [ String ] deriving (Eq, Show)

newR :: [ String ] -> Route
newR = Rou  -- Construye una ruta con la lista de ciudades

inOrderR :: Route -> String -> String -> Bool
inOrderR (Rou cities) city1 city2 = case (elemIndex city1 cities, elemIndex city2 cities) of
  (Just i1, Just i2) -> i1 < i2
  _ -> False

-- Definición de Stack
data Stack = Sta [ Palet ] Int deriving (Eq, Show)

newS :: Int -> Stack
newS capacity = Sta [] capacity  -- Nueva pila vacía con capacidad

freeCellsS :: Stack -> Int
freeCellsS (Sta pallets capacity) = capacity - length pallets  -- Celdas libres

stackS :: Stack -> Palet -> Stack
stackS (Sta pallets capacity) p = Sta (p : pallets) capacity  -- Apila un palet

netS :: Stack -> Int
netS (Sta pallets _) = sum (map netP pallets)  -- Peso total de la pila

holdsS :: Stack -> Palet -> Route -> Bool
holdsS (Sta pallets capacity) p route = 
  freeCellsS (Sta pallets capacity) > 0 && 
  netS (Sta pallets capacity) + netP p <= 10 && 
  (null pallets || inOrderR route (destinationP p) (destinationP (head pallets)))

popS :: Stack -> String -> Stack
popS (Sta pallets capacity) city = Sta (dropWhile ((== city) . destinationP) pallets) capacity  -- Quita los palets del destino

-- Definición de Truck
data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck
newT bays height route = Tru (replicate bays (newS height)) route  -- Camión con bahías vacías

freeCellsT :: Truck -> Int
freeCellsT (Tru stacks _) = sum (map freeCellsS stacks)  -- Celdas disponibles totales

loadT :: Truck -> Palet -> Truck
loadT (Tru stacks route) palet = Tru (loadInStacks stacks palet route) route  -- Carga un palet

unloadT :: Truck -> String -> Truck
unloadT (Tru stacks route) city = Tru (map (`popS` city) stacks) route  -- Descarga palets de la ciudad

netT :: Truck -> Int
netT (Tru stacks _) = sum (map netS stacks)  -- Peso total del camión

-- Funciones de carga y descarga
loadTruck :: Truck -> Palet -> Truck
loadTruck (Tru stacks route) palet = Tru (loadInStacks stacks palet route) route

loadInStacks :: [Stack] -> Palet -> Route -> [Stack]
loadInStacks [] _ _ = []  -- No hay bahías disponibles
loadInStacks (s:ss) p route
  | holdsS s p route = stackS s p : ss  -- Si la pila acepta el palet, lo apilamos
  | otherwise = s : loadInStacks ss p route  -- Probamos con la siguiente bahía

unloadTruck :: Truck -> String -> Truck
unloadTruck (Tru stacks route) city = Tru (map (`popS` city) stacks) route


-- Ejemplo ----------------
-- Crear una ruta con tres ciudades
rutaEjemplo :: Route
rutaEjemplo = newR ["A", "B", "C"]

-- Crear un camión con 2 bahías y 5 espacios en cada una
camionEjemplo :: Truck
camionEjemplo = newT 2 5 rutaEjemplo

-- Crear palets con distintos destinos y pesos
palet1 :: Palet
palet1 = newP "A" 3

palet2 :: Palet
palet2 = newP "B" 4

palet3 :: Palet
palet3 = newP "C" 2

palet4 :: Palet
palet4 = newP "B" 5

-- Cargar palets en el camión
camionCargado :: Truck
camionCargado = loadT (loadT (loadT (loadT camionEjemplo palet1) palet2) palet3) palet4

-- Consultar espacios libres antes de la entrega
espaciosAntes :: Int
espaciosAntes = freeCellsT camionCargado

-- Consultar peso total antes de la entrega
pesoAntes :: Int
pesoAntes = netT camionCargado

-- Descargar en la ciudad "A"
camionDespuesA :: Truck
camionDespuesA = unloadT camionCargado "A"

-- Descargar en la ciudad "B"
camionDespuesB :: Truck
camionDespuesB = unloadT camionDespuesA "B"

-- Consultar espacios y peso después de las entregas
espaciosDespues :: Int
espaciosDespues = freeCellsT camionDespuesB

pesoDespues :: Int
pesoDespues = netT camionDespuesB