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
rutaCorta = [ba, mdq, tuc]
rutaLarga = [tuc, sla, ba, mdq, bhi, rga, ush]

-- stacks
-- no estamos probando holdsS, por lo que no importa que los palets sean compatibles para probar el peso
stack0 = newS 5
stack1 = stackS stack0 p1
stack2 = stackS (stackS stack0 p2) p1
stack5 = stackS (stackS (stackS (stackS (stackS stack0 p1) p2) p3) p4) p5 

stackRutaCorta = stackS (stackS stack0 p4) p10 -- test holdS con p9
stackRutaLarga = stackS (stackS (stackS (stackS stack0 p4) p6) p8) p3



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
    freeCellsS stack0 == 5,
    freeCellsS stack2 == 3,
    freeCellsS stack5 == 0,
    netS stack0 == 0,
    netS stack2 == 6,
    netS stack5 == 12
    ]
