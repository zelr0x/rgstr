(ns rgstr.util)

(defn compute-if-absent [m k f]
  (if (contains? m k) m (assoc m k (f m k))))

(defn ensure-assoc [m k f]
  (let [m (or m {})]
    (compute-if-absent m k f)))
