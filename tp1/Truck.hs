module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )
  where

import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT cantBahias alturaMax ruta = Tru (replicate cantBahias (newS alturaMax)) ruta

-- Helper function - por si no se puede usar replicate usamos este
-- createEmptyStacks :: Int -> Int -> [Stack]
-- createEmptyStacks 0 _ = []
-- createEmptyStacks n height = newS height : createEmptyStacks (n - 1) height

freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
freeCellsT (Tru [] ruta) = 0
freeCellsT (Tru (s:stacks) ruta) = freeCellsS s + freeCellsT (Tru stacks ruta)

loadT :: Truck -> Palet -> Truck -- carga un palet en el camion
loadT (Tru [] _) _ = error "No hay espacio en el camion"
loadT (Tru (s:stacks) ruta) p
    | holdsS s p ruta = Tru (stackS s p : stacks) ruta
    | otherwise = case loadT (Tru stacks ruta) p of
        Tru newStacks newRuta -> Tru (s : newStacks) newRuta

unloadT :: Truck -> String -> Truck -- responde un camion al que se le han descargado los paletes que podían descargarse en la ciudad
unloadT (Tru [] _) _ = error "No hay palets en el camion"
unloadT (Tru stacks ruta) destino = Tru (mapFold (`popS` destino) stacks) ruta

netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion
netT (Tru [] ruta) = 0
netT (Tru (s:stacks) ruta) = netS s + netT (Tru stacks ruta)


mapFold :: (a -> b) -> [a] -> [b]
mapFold f = foldr (\x acc -> f x : acc) []