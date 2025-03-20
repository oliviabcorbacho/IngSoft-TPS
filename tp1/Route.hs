--Codigo dado por emilio
module Route ( Route, newR, inOrderR, inRouteR)
   where

data Route = Rou [ String ] deriving (Eq, Show)

newR :: [ String ] -> Route -- construye una ruta según una lista de ciudades
newR ruta | ruta == [] = error "La ruta no puede ser vacía"
          | otherwise = Rou ruta


--funcion auxiliar
before :: String -> String -> [String] -> Bool
before _ _ [] = False
before c1 c2 (c:cities)
  | c == c1   = elem c2 cities  -- encontre la primera ciudad, quiero ver si existe dsp la segunda
  | c == c2   = False       -- encontre la segunda ciudad antes que la primera
  | otherwise = before c1 c2 cities  -- Me fijo en lo que sigue de la lista


inOrderR :: Route -> String -> String -> Bool -- indica si la primer ciudad consultada está antes que la segunda ciudad en la ruta
inOrderR (Rou cities) c1 c2 | c1 == c2 = True
                            | otherwise = before c1 c2 cities

inRouteR :: Route -> String -> Bool -- indica si la ciudad consultada está en la ruta
inRouteR (Rou cities) c  = elem c cities