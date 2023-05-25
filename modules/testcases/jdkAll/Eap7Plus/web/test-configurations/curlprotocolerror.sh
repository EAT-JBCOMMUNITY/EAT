# @EAT({"modules/testcases/jdkAll/Eap7Plus/web/test-configurations","modules/testcases/jdkAll/WildflyJakarta/web/test-configurations"}) 
            
curl --http2 -X PUT  localhost:8081/ -d foo -X PUT -H 'Expect: 100-continue' --http2-prior-knowledge -v &> output.txt
