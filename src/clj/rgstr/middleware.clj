(ns rgstr.middleware)

(defn- strip-nonroot-trailing-slash [uri]
  (if (and (not= "/" uri)
           (.endsWith uri "/"))
    (subs uri 0 (dec (count uri)))
    uri))

;; Task requirement is to use compojure. With reitit use:
;; (ring/redirect-trailing-slash-handler {:method :strip})
(defn strip-nonroot-trailing-slash-handler [handler] (fn [request]
  (let [uri (request :uri)
        clean-uri (strip-nonroot-trailing-slash uri)]
    (handler (assoc request :uri clean-uri)))))
