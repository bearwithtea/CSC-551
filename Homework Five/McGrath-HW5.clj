;; light speed
;; Define a function named delay-in-sec that takes one input, a distance in miles, and returns the number of seconds it takes for light to travel that distance. The return value should be rounded to the nearest integer. For example, (delay-in-sec 93000000) should evaluate to 499.

(defn delay-in-sec [distance]
  (int (Math/round (/ distance 186262.00))))

(delay-in-sec 93000000)

;; Define a function named sec->min that takes one input, a number of seconds, and returns the corresponding number of minutes. Note that this function should always return a decimal value (not a rational). For example, (sec->min 150) should evaluate to 2.5.

(defn sec->min [seconds]
  (double (/ seconds 60)))

(sec->min 150)

;;  Define a function named delay-in-min that takes one input, a distance in miles, and returns the number of minutes it takes for light to travel that distance. For example, (delay-in-min 93000000) should evaluate to approximately 8.3.

(defn delay-in-min [distance]
  (sec->min (delay-in-sec distance)))

(delay-in-min 93000000)

;; relatively speaking
;; Define a function named observer-time that takes two inputs, a distance in light years and a fraction of light speed, and returns the number of years it would take to travel that distance to an outside observer. For example, (observer-time 4.2 0.5) should evaluate to 8.4.

(defn observer-time [distance-in-light-speed frac-of-light-speed]
  (/ distance-in-light-speed frac-of-light-speed))

(observer-time 4.2 0.5)

;; Define a function named dilation-factor that takes one input, a fraction of light speed, and returns the corresponding dilation factor. For example, (dilation-factor 0.5) should evaluate to approximately 0.866.

(defn dilation-factor [frac-of-light-speed]
  (/ 1 (Math/sqrt (- 1 (Math/pow frac-of-light-speed 2)))))

(dilation-factor 0.5)

;; Define a function named traveler-time that takes two inputs, a distance in light years and a fraction of light speed, and returns the number of years it would take to travel that distance to a traveler. For example, (traveler-time 4.2 0.5) should evaluate to approximately 7.27.

(defn traveler-time [distance-in-light-speed frac-of-light-speed]
  (/ (observer-time distance-in-light-speed frac-of-light-speed)
     (dilation-factor frac-of-light-speed)))

(traveler-time 4.2 0.5)

;; "fun" with lists
;; While both functions behave correctly if the specified index is in bounds (0 ≤ index < list size), they return different values if the index is out of bounds. The latter simply returns an unchanged copy of the list. The former returns a bizarre list, with nils added at the end. Modify remove-nth-A so that it behaves the same as remove-nth-B (i.e., returns the unchanged list) when the index is out of bounds. 

(defn remove-nth-A [arblist n]
  (if (or (zero? n) (empty? arblist)) 
    (rest arblist) 
    (cons (first arblist) (remove-nth-A (rest arblist) (dec n))))) 

(remove-nth-A '(1 2 3 4 5) 0)
(remove-nth-A '(1 2 3 4 5) 9)

(defn remove-nth-B  [arblist n]
  (concat (take n arblist) (nthrest arblist (inc n)))) 

(remove-nth-B '(1 2 3 4 5) 2)
(remove-nth-B '(1 2 3 4 5) 9)

;; Define similar functions insert-nth-A and insert-nth-B, which return a copy of the list with a new value inserted into the specified index. For example, (insert-nth-A '(:a :b :c :d) 2 :x) and (insert-nth-B '(:a :b :c :d) 2 :x) should both return (:a :b :x :c :d) . As before, these functions should return an unchanged copy of the list if the index is out of bounds. 

(defn insert-nth-A [arblist n x]
  (if (and (>= n 0) (< n (count arblist))) ;;confirms that the index is in bounds
    (if (zero? n) ;;if the index is 0
      (cons x arblist) ;;adds the new element to the front of the list
      (cons (first arblist) (insert-nth-A (rest arblist) (dec n) x))) ;;otherwise, it keeps the first element and calls the function recursively on the rest of the list, decrementing n
    arblist)) ;;returns the original list if index is out of bounds

(insert-nth-A '(:a :b :c :d) 2 :x)
(insert-nth-A '(:a :b :c :d) 9 :x)

(defn insert-nth-B [arblist n x]
  (if (and (>= n 0) (< n (count arblist))) ;;confirms that the index is in bounds
    (concat (take n arblist) (list x) (nthrest arblist n)) ;; takes the first n elements, adds the new element, and then adds the rest of the list starting at index n
    arblist)) ;; returns the original list if index is out of bounds

(insert-nth-B '(:a :b :c :d) 2 :x)
(insert-nth-B '(:a :b :c :d) 9 :x)

;; Define a function named replace-nth that takes three inputs, a list, an index and a value, and returns a copy of the list but with the new value replacing the value at the specified index. For example, (replace-nth '(:a :b :c :d) 2 :x) should return (:a :b :x :d) . As before, it should return an unchanged copy of the list if the index is out of bounds. 

(defn replace-nth [arblist n x]
  (if (and (>= n 0) (< n (count arblist))) ;;confirms that the index is in bounds
    (concat (take n arblist) (list x) (nthrest arblist (inc n))) ;; takes the first n elements, adds the new element, and then adds the rest of the list starting at index n
    arblist)) ;; returns the original list if index is out of bounds

(replace-nth '(:a :b :c :d) 2 :x)
(replace-nth '(:a :b :c :d) 9 :x)

;; Define a function named inc-nth that takes two inputs, a list of numbers and an index, and returns a copy of the list with the number at the specified index incremented. For example, (inc-nth '(10 20 30) 0) should return (11 20 30) . As before, it should return an unchanged copy of the list if the index is out of bounds. 

(defn inc-nth [arblist n]
  (if (and (>= n 0) (< n (count arblist))) ;;confirms that the index is in bounds
    (concat (take n arblist) (list (+ 1 (nth arblist n))) (nthrest arblist (inc n))) ;; takes the first n elements, adds 1 to the nth element, and then adds the rest of the list starting at index n
    arblist)) ;; returns the original list if index is out of bounds

(inc-nth '(10 20 30) 0)
(inc-nth '(10 20 30) 2)

;; "fun" with recursion
;; Define a function named dice-roll that has one input, the number of sides, and returns the sum of two die rolls with that number of sides each. For example, (dice-roll 8) would return the sum of two 8-sided dice.

(defn dice-roll [sides]
  (+ (inc (rand-int sides)) (inc (rand-int sides)))) ;; generates a random number between 1 and the number of sides, inclusive, for two dice and adds them together

(dice-roll 8)

;; Define a function named count-rolls that takes three inputs, all positive integers, representing a number of rolls, the number of die sides and a desired dice total. The function should repeatedly call the dice-roll function to simulate the specified number of rolls and return the number of times the desired total was obtained. For example, the call (count-rolls 1000 6 7) should simulate 1000 6-sided dice rolls and return the number of times 7 was rolled. Since the number of rolls could be large, your function should utilize tail-recursion. 

(defn count-rolls [num-rolls sides desired-total]
  (defn count-helper [rolls-left count-so-far] ;; helper function to keep track of rolls left and count of desired total
    (if (zero? rolls-left) ;; base case: if no rolls left, return count 
      count-so-far ;; return the count of desired total
      (let [roll (dice-roll sides)] ;; roll the dice, there has to be a let binding here since we need to use the roll in the next line idk how else to do it
        (if (= roll desired-total) ;; check if the roll matches the desired total
          (count-helper (dec rolls-left) (inc count-so-far)) ;; increment count if it matches
          (count-helper (dec rolls-left) count-so-far))))) ;; otherwise, just decrement rolls left

  (count-helper num-rolls 0)) ;; call the helper function with initial rolls and count, helps to keep track of the of the count so far 

(count-rolls 1000 6 7)

;; Define a function named count-all that takes two inputs, both positive integers, representing a number of rolls and the number of die sides. The function should repeatedly call the dice-roll function to simulate that many rolls and return a list with the counts for all dice totals. For example, the call (count-all 10000 6) might return (288 526 824 1093 1388 1708 1413 1115 811 563 271), signifying that 288 rolls totaled 2, 526 rolls totaled 3, 824 rolls totaled 4, etc. Since the number of rolls could be large, your function should utilize tail-recursion. Note: you should be able to make use of your inc-nth function here.

(defn count-all [num-rolls sides]
  (defn count-helper [rolls-left counts] ;; helper function to keep track of rolls left and counts
    (if (zero? rolls-left) ;; base case: if no rolls left, return counts
      counts
      (let [roll (dice-roll sides)] ;; roll the dice
        (count-helper (dec rolls-left) (inc-nth counts (- roll 2)))))) ;; adjust index by subtracting 2

  ;; initialize with zeros for totals 2 through 2*sides (inclusive)
  (count-helper num-rolls (repeat (- (* 2 sides) 1) 0)))

(count-all 10000 6)