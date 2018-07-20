#########################################
# AUTOR: Omar Suárez López
# ASIGNATURA: Big Data
# PRACTICA: Hadoop
# CURSO: 2017/2018
#########################################

Anotaciones sobre la práctica:
------------------------------


1) Para el cálculo de los 'momentum' se ha tenido en cuenta que:

Los valores se han ordenado según el timestamp del más reciente (primer valor)
al más antiguo (último valor) de modo que:
	
	(K, list[v1, v2, v3, .... v_n])

	+ t_0 => tweet mas reciente (primer valor de la lista)
	+ t_N => tweet mas antiguo (último valor de la lista)



2) Las clases que componen el proyecto son:

	+ TweetMapper
	+ TweetReducer
	+ TweetJob (ejecuta el trabajo MapReduce)
	+ UserTimestampPair (define la composite key)
	+ UserTimestampPartitioner (particiona basandose en la natural key, todos los tweets del mismo usuario van al mismo reducer)
	+ UserTimestampGroupingComparator (compara la natural key)
	+ TweetValue (natural values (timestamp, numRetweets))
	+ Tweet

3) El campo 'retweeted' se utiliza para marcar si el tweet es un retweet o es original del usuario. En este
caso todos los tweets presentes son originales así que tienen este campo a 'false'. 
El campo utilizado para saber cuantas veces se ha retweeteado al usuario es 'retweet_count'



Pasos para ejecutar el código:
------------------------------

En este documento se resumen los pasos para poder ejecutar el proyecto creado para la práctica de Hadoop.


1)	Lanzar el cluster => java_cluster.sh

2)	Comprobamos que los 4 DataNodes están funcionando => hdfs dfsadmin -report

3)	Comprobamos que hay 4 NodeManagers => yarn node -list

4) 	Creamos los directorios en el hdfs y subimos el .json:
		hadoop fs -mkdir tweets_secondary_sort
		hadoop fs -mkdir tweets_secondary_sort/input
		hadoop fs -mkdir tweets_secondary_sort/output
		hadoop fs -put ~/cache-1000000.json tweets_secondary_sort/input
		hadoop fs -ls tweets_secondary_sort/input

5)  Si existiese ya un fichero de salida lo eliminamos
        OUTPUT=/tweets_secondary_sort/output/tweets_out
        hadoop fs -rmr $OUTPUT    
        
6) Generamos el JAR del proyecto utilizando Gradle

7) Nos vamos a la carpeta donde está el fichero JAR y lo lanzamos
        hadoop jar practica_hadoop.jar tweets_secondary_sort/input/cache-1000000.json tweets_secondary_sort/output/tweets_out
        
        
8) Cuando el job termine extraemos los resultados a un .csv 
        hadoop fs -getmerge tweets_secondary_sort/output/tweets_out ~/Desktop/result.csv

9) Obtenemos los datos correspondientes a los usuarios que se nos preguntan 
        fgrep '00ASHLEYMARIE00' result.csv
        fgrep '007_Debby' result.csv
        
        
        
Los resultados son:
root@ubuntu:~/Desktop# fgrep '00ASHLEYMARIE00' result.csv
00ASHLEYMARIE00,19,0.0,0.405

root@ubuntu:~/Desktop# fgrep '007_Debby' result.csv
007_Debby,26,0.0,0.419        
        
        