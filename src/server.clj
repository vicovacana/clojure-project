(ns server
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer [response]]
            [maze :as m]
            [maze-solver :as s]))

(defroutes app-routes
  (GET "/" []
    (response {:mess "Server is listening"}))
  
(POST "/generate" request
        (let [body (:body request)
              size (:size body 10) 
              maze-data (m/generate-maze size size)]
          (response maze-data)))
  
  (POST "/solve-a-star" request
    (let [body (:body request)
          grid (:grid body)
          start (:start-cell body)
          end (:end-cell body)
          
          solution (s/solve-a-star grid start end)]
      
      (response solution)))
  
  (route/not-found (response {:err "Route not found"})))

;;middleware (JSON to map and map to JSON)
(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      (wrap-json-response)))

(defn -main []
  (println "Server is running....")
  (run-jetty app {:port 3000 :join? false}))

;;Jetty Eclipse je Java HTTP web server