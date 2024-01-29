(ns bank-sim-app.core
  (:gen-class :main true))

;----------------------------------;
;       GLOBAL STATES/FUNCS        ;
;----------------------------------;


(def bank-sys (ref {}))  
(def user-ref (ref nil)) ; this is a catch, essentially 
(defrecord Account [id fname lname amount])

(defn print-world []
  (do 
    (println "bank sys: " bank-sys)
    (println "user: " user-ref)
    nil))

(defn reset-world! [] 
    (dosync 
     (ref-set bank-sys {})
     (ref-set user-ref nil)))

(defn user-input [message]
  (let [input-message (do
                        (println message)
                        (read-line))]
    input-message))

;----------------------------------;
;         ADD USER TO DB           ;
;----------------------------------;

(defn generate-user-ID []
    (let [bank-id (count (vals @bank-sys))]
         (if (= 1 bank-id)
           (hash 0)
           (hash (inc bank-id)))))

(defn account-generator [fname lname]
  "not going to be used, but available for now"
  (->Account (generate-user-ID) fname lname 0.0))

(defn get-fullname [account]
  "not going to be used, but available for now"
  (keyword (str (:fname account) (:lname account))))

(defn add-user! [bank-sys account]
  "replacing (generate-counter) with (str :fname :lname)
    I ASSUME HERE that all names are unique. Doing this to make more progress
    and not have to deal with sign in and sign up functions." 
   (alter bank-sys assoc (get-fullname account) account))

;----------------------------------;
;           USER ACTIONS           ;
;----------------------------------;
;;; need to make the UI have options to go back or to main screen. B for back and Q for quit.
;;; build that out as a cond, prob.

(defn deposit! [amount]
  (alter bank-sys update-in [(ensure user-ref) :amount] + amount))

(defn withdraw! [amount]
  (let [user-keyword (keyword (ensure user-ref)) ;keyword func may be redundant.
        user-amount (:amount (user-keyword (ensure bank-sys)))]
    (when (> (- user-amount amount) 0)
      (alter bank-sys update-in [(ensure user-ref) :amount] - amount)))) ;;ensure seems redundant again.

(defn transfer! [to-account-ID amount]
  (let [from-account-ID (ensure user-ref)
        from-account-hashmap (from-account-ID (ensure bank-sys))
        from-account-amount (:amount from-account-hashmap)]
    (when (> (- from-account-amount amount) 0)
      (alter bank-sys update-in [from-account-ID :amount] - amount)
      (alter bank-sys update-in [to-account-ID :amount] + amount))))

;----------------------------------;
;           MAIN PAGE FUNC         ;
;----------------------------------;

(def make-input-to-keyword (comp #(keyword %) (partial apply str) #(clojure.string/split % #" ")))

(defn sign-out []
    (dosync
     (ref-set user-ref nil))) 

(defn main-page! []
  (defn account-status-UI! []
    (let [account (@user-ref @bank-sys)]
      (do
        (println account "\n\n")
        (main-page!))))
  (defn deposit-UI! []
    (let [user-response (user-input "How much would you like to deposit USD?")
          deposited-amount (read-string user-response)]
      (do
        (dosync (deposit! deposited-amount))
        (println "Deposit successful! You deposited: $"  deposited-amount "\n")
        (main-page!))))
  (defn wihtdraw-UI! []
    (let [user-response (user-input "How much would you like to withdraw USD?")
          withdraw-amount (read-string user-response)
          user-amount (:amount (@user-ref @bank-sys))]
      (when (> (- user-amount withdraw-amount) 0)
        (println "You withdrew: $" withdraw-amount)
        (println "Your new balance is: $" (- user-amount withdraw-amount))
        (dosync
         (withdraw! withdraw-amount))
        (main-page!))
      (do 
        (println "You cannot withdraw this amount because your account will be below $0.0\n")
        (wihtdraw-UI!))))
  (defn transfer-UI! []
    (let [user-res-amount (user-input "How much would you like to transfer USD?\n")
          user-res-account (user-input "Whose account do you want to send it to?\n")
          send-amount (read-string user-res-amount)
          to-account-keyword (make-input-to-keyword user-res-account)]
      (do
        (println "You sent $" send-amount "to " user-res-account) 
        (dosync (transfer! to-account-keyword send-amount)) 
        (println "Your new account balance is $" (:amount (@user-ref @bank-sys)))
        (println "Returning you to main page...\n")
        (main-page!)))) 
  (defn unregistered-key-response-UI! []
    (do
      (println "This input is unrecognized. You'll be sent back to the main page.")
      (main-page!))) 
  (let [input (user-input "Would you like to check account, deposit,
                           withdraw, transfer funds, or log off?\n
                           Press (0) to check account
                           Press (1) to deposit
                           Press (2) to withdraw
                           Press (3) to transfer
                           Press (4) to log off\n\n")
        res (read-string input)] 
      (cond
        (= res 0) (account-status-UI!)
        (= res 1) (deposit-UI!)
        (= res 2) (wihtdraw-UI!)
        (= res 3) (transfer-UI!)
        (= res 4) (do
                    (println "Being signed out...")
                    (println "Logged out")
                    (sign-out))
        :else (unregistered-key-response-UI!))))

;----------------------------------;
;      FRONT PAGE FUNCTIONALITY    ;
;----------------------------------;

(defn sign-up! []
  (let [sign-in-info (user-input "Sign up: supply first and last name to loggin\n")
        user-name-vec (clojure.string/split sign-in-info #" ")
        [fname lname] user-name-vec
        input (apply str fname lname)]
    (dosync
     (add-user! bank-sys (account-generator fname lname))
     (ref-set user-ref (keyword input)))))

(defn sign-in! [user-ID]
  (dosync
   (ref-set user-ref user-ID)))


(defn sign-up->main-page! []
  (do
    (sign-up!)
    (println "Account created! Directing you to main page...\n")
    (main-page!)))

(defn sign-in->main-page! []
  (let [user-keyword-ID (make-input-to-keyword (user-input "Sign in: supply first and last name to loggin\n"))
        in-db? (not (nil? (user-keyword-ID @bank-sys)))]
    (when in-db?
      (sign-in! user-keyword-ID)
      (println "Sign in successful! Being directed to home page...\n")
      (main-page!))
    (do
      (println "This account is not in our db. Let's sign you up.")
      (sign-up->main-page!))))

(defn front-page! [] 
  (let [user-response (user-input "Would you like to sign-in or sign-up?\n
                                Press (1) to sign-in.
                                Press (2) to sign-up.\n\n")
        is-one (= 1 (read-string user-response))]
    (if is-one
      (sign-in->main-page!)
      (sign-up->main-page!))))

(defn -main []
  (front-page!))

(-main)