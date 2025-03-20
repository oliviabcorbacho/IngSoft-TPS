module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS, popS )
  where

import Palet
import Route
import Distribution.Parsec (parsecLeadingOptCommaList)

data Stack = Sta [ Palet ] Int deriving (Eq, Show)

newS :: Int -> Stack                      -- construye una Pila con la capacidad indicada 
newS capacidad  | capacidad <= 0 = error "La capacidad debe ser mayor a 0"
                | otherwise = Sta [] capacidad

freeCellsS :: Stack -> Int                -- responde la celdas disponibles en la pila
freeCellsS (Sta palets capacidad) = capacidad - length palets

stackS :: Stack -> Palet -> Stack         -- apila el palet indicado en la pila
stackS (Sta palets capacidad) p = Sta ( p: palets ) capacidad

netS :: Stack -> Int                      -- responde el peso neto de los paletes en la pila
netS (Sta [] capacidad) = 0
netS (Sta (p:palets) capacidad) = netP p + netS (Sta palets capacidad)

holdsS :: Stack -> Palet -> Route -> Bool -- indica si la pila puede aceptar el palet considerando las ciudades en la ruta
holdsS (Sta [] capacidad) p ruta = True -- si la pila esta vacia True
holdsS (Sta (topp:palets) capacidad) p ruta | freeCellsS (Sta (topp:palets) capacidad) == 0 = False -- si no hay celdas disponibles False
                                            | netS (Sta (topp:palets) capacidad) + netP p >= 10 = False -- si el peso es mayor a 10 False
                                            | (inRouteR ruta (destinationP p)) == False = False -- si la ciudad de destino no esta en la ruta False
                                            | otherwise = inOrderR ruta (destinationP p) (destinationP topp) -- si la ciudad de destino esta en la ruta, se fija si la ciudad de destino del palet es anterior a la ciudad de destino del tope


popS :: Stack -> String -> Stack          -- quita del tope los paletes con destino en la ciudad indicada
popS (Sta [] capacidad) destino = Sta [] capacidad
popS (Sta (p:palets) cap) destino | destinationP p /= destino = Sta (p:palets) cap
                                  | destinationP p == destino = popS (Sta palets cap) destino