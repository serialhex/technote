(ns technote.qbclient
  (:import (java.net Socket)
           (java.io PrintWriter InputStreamReader BufferedReader)))
; code mostly stolen from
; http://nakkaya.com/2010/02/10/a-simple-clojure-irc-client/

(declare conn-handler)

(defn connect [server]
  (let [socket (Socket. (:name server) (:port server))
        in (BufferedReader. (InputStreamReader. (.getInputStream socket)))
        out (PrintWriter. (.getOutputStream socket))
        conn (ref {:in in :out out})]
    (doto (Thread. #(conn-handler conn)) (.start))
    conn))

(defn write [conn msg]
  (doto (:out @conn)
    (.println (str msg "\r"))
    (.flush)))

(defn- conn-handler [conn]
  (while (nil? (:exit @conn))
    (let [msg (.readLine (:in @conn))]
      (println msg))))

(defn disconnect [conn]
  (dosync (alter conn merge {:exit true})))

; (def serv {:name "192.168.56.101" :port 3000})
; (def lokal (connect serv))

; some test queries, the first one succeeds, the second one fails.
; (def qry1 "<?xml version=\"1.0\" encoding=\"utf-8\"?><?qbxml version=\"6.0\" ?><QBXML><QBXMLMsgsRq onError=\"stopOnError\"><CustomerQueryRq iterator=\"Start\"><MaxReturned>1</MaxReturned></CustomerQueryRq></QBXMLMsgsRq></QBXML>\n")
; (def qry2 "<?xml version=\"1.0\" encoding=\"utf-8\"?><?qbxml version=\"6.0\" ?><QBXML><QBXMLMsgsRq onError=\"stopOnError\"><CustomerQueryRq iterator=\"Start\"><FullName>llama</FullName><MaxReturned>1</MaxReturned></CustomerQueryRq></QBXMLMsgsRq></QBXML>\n")
