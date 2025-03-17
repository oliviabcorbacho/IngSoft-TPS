module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS )
  where

import Palet
import Route
import Distribution.Parsec (parsecLeadingOptCommaList)

data Stack = Sta [ Palet ] Int deriving (Eq, Show)

newS :: Int -> Stack                      -- construye una Pila con la capacidad indicada 
newS capacidad = Sta [] capacidad


freeCellsS :: Stack -> Int                -- responde la celdas disponibles en la pila
freeCellsS (Sta palets capacidad) = capacidad - length palets

--funcion auxiliar - PREGUNTAR EMILIO QUE NOS CONVIENE MAS
append :: [Palet] -> Palet -> [Palet]
append [] p = [p]
append palets p = palets ++ [p]

stackS :: Stack -> Palet -> Stack         -- apila el palet indicado en la pila
stackS (Sta palets capacidad) p = Sta ( p: palets ) capacidad
--stackS (Sta pallets capacity) p = Sta (p : pallets) capacity  -- Apila un palet

netS :: Stack -> Int                      -- responde el peso neto de los paletes en la pila
netS (Sta [] capacidad) = 0
netS (Sta (p:palets) capacidad) = netP p + netS (Sta palets capacidad)



holdsS :: Stack -> Palet -> Route -> Bool -- indica si la pila puede aceptar el palet considerando las ciudades en la ruta
holdsS (Sta palets capacidad) p ruta | freeCellsS (Sta palets capacidad) == 0 = False
                                     | netS (Sta palets capacidad) + netP p >= 10 = False
                                     | (inRouteR ruta (destinationP p)) == False = False
                                     | null (getCities ruta) = False
                                     | otherwise = inOrderR ruta (head (getCities ruta)) (destinationP p)


popS :: Stack -> String -> Stack          -- quita del tope los paletes con destino en la ciudad indicada
popS (Sta (p:palets) cap) destino | destinationP p /= destino = Sta (p:palets) cap
                                  | destinationP p == destino = popS (Sta palets cap) destino