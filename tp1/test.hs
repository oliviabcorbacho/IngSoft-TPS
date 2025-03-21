module Test where
import Truck
import Palet
import Route
import Stack
import Distribution.Parsec (parsecLeadingCommaList)
import Control.Exception
import System.IO.Unsafe
import Foreign (free)

testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()


-- variables
ba = "Buenos Aires"
mdq = "Mar del Plata"
bhi = "Bahia Blanca"
tuc = "Tucuman"
ush = "Ushuaia"
sla = "Salta"
rga = "Rio Gallegos"

-- palets
p1 = newP ba 5
p2 = newP mdq 1
p3 = newP bhi 2
p4 = newP tuc 3
p5 = newP ush 1
p6 = newP sla 2
p7 = newP rga 3
p8 = newP ba 2
p9 = newP ba 3
p10 = newP mdq 4

-- rutas
rutaCorta = newR [ba, mdq, tuc]
rutaLarga = newR [tuc, sla, ba, mdq, bhi, rga, ush]

-- stacks
-- no estamos probando holdsS, por lo que no importa que los palets sean compatibles para probar el peso
stack0 = newS 5
stack1 = stackS stack0 p1
stack2 = stackS (stackS stack0 p2) p1
stack222 = stackS (stackS (stackS (stackS stack0 p1) p2) p2) p2
stack3 = stackS (stackS (stackS stack0 p1) p2) p3
stack5 = stackS (stackS (stackS (stackS (stackS stack0 p1) p2) p3) p4) p5 

stackRutaCorta = stackS (stackS stack0 p4) p10 -- test holdS con p9
stackRutaLarga = stackS (stackS (stackS (stackS stack0 p4) p6) p8) p3

truck1 = newT 1 2 rutaCorta
truck11 = loadT truck1 p1
truck_full = loadT truck1 p8
truck2 = newT 2 3 rutaLarga
truck22 = loadT truck2 p1
truck222 = loadT truck22 p4

test_palet = [
    testF (newP "" 5),
    testF (newP ba (-1)),
    testF (newP ba 3),
    testF (destinationP p1),
    testF (netP p1) 
    ]
test_stack = [
    testF (newS (-1)),
    testF (newS 0),
    testF (newS 5),

    not(freeCellsS stack0 == 5),
    not(freeCellsS stack2 == 3),
    not(freeCellsS stack5 == 0),
    
    not(netS stack0 == 0),
    not(netS stack2 == 6),
    not(netS stack5 == 12),
    
    not(popS stack0 ba == stack0),
    not(popS stack1 ba == stack0),
    not(popS stack222 mdq == stack1),

    not(holdsS stack5 p6 rutaCorta == False),
    not(holdsS stack3 p4 rutaCorta == False),
    not(holdsS stackRutaCorta p3 rutaCorta == False),
    not(holdsS stack0 p1 rutaCorta == True),
    not(holdsS stack1 p9 rutaCorta == True),
    not(holdsS stackRutaCorta p8 rutaCorta == True),
    not(holdsS stack1 p2 rutaCorta == False)
    ]

test_route = [
    testF (newR []),
    testF (newR [ba, mdq]),
    
    not(inOrderR rutaCorta ba mdq == True),
    not(inOrderR rutaCorta mdq ba == False),
    not(inOrderR rutaCorta ba ba == True),
    
    not(inRouteR rutaCorta ba == True),
    not(inRouteR rutaCorta bhi == False)
    ]

test_truck = [
    testF (newT 0 1 rutaCorta),
    testF (newT 1 0 rutaCorta),
    testF (newT 1 1 rutaCorta),

    not(freeCellsT truck1 == 2),
    not(freeCellsT truck11 == 1),
    not(freeCellsT truck222 == 4),

    testF (loadT truck1 p1), -- camion vacio
    testF (loadT truck_full p2), -- camion lleno
    testF (loadT truck11 p8), -- mismo destino
    testF (loadT truck22 p4 ), -- distinto destino
    testF (loadT truck1 p2), -- mal orden

    testF (unloadT truck1 ba), -- camion vacio
    testF (unloadT truck11 ba), -- destino descargable
    testF (unloadT truck222 ba), -- destino no descargable

    not(netT truck1 == 0),
    not(netT truck_full == 2),
    not(netT truck222 == 8)
    ]


