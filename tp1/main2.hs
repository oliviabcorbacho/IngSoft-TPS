before :: String -> String -> [String] -> Bool
before _ _ [] = False
before c1 c2 (c:cities)
  | c == c1   = elem c2 cities  -- encontre la primera ciudad, quiero ver si existe dsp la segunda
  | c == c2   = False       -- encontre la segunda ciudad antes que la primera
  | otherwise = before c1 c2 cities  -- Me fijo en lo que sigue de la lista



