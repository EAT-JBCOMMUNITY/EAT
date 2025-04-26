# @EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/test-configurations","modules/testcases/jdkAll/Eap7Plus/jaxrs/test-configurations"}) 

curl -X POST --location "http://localhost:8080/testdeployment/rest/test/multipart" --http1.1 \
    -H "Content-Type: multipart/mixed; boundary=WebAppBoundary" \
    -H "Accept: text/plain" \
    -d "--WebAppBoundary
Content-Disposition: name=\"IMAGE\"; filename=\"test.png\"
Content-Transfer-Encoding: BASE64
Content-Type: image/png

iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAIAAAAC64paAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAfSURBVDhPYxg48J8CMKqZRDCqmUQwqplEMFCa//8HAJfZNAWgNbc2AAAAAElFTkSuQmCC

--WebAppBoundary--" &> output2.txt
