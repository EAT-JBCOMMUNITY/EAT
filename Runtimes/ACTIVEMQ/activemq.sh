#!/bin/bash
rm -R ActivemqAT
git clone https://github.com/panossot/ActivemqAT.git
cd ActivemqAT
mvn clean install -Dmaster > ../output.txt

echo "$(cat ../output.txt)"

if [ -f "../errors.txt" ]
then
  rm ../errors.txt
fi

if [[ $(cat ../output.txt) == *"ERROR"* ]]
then
    echo  >> ../errors.txt
    (echo "Active MQ Additional Testsuite was completed with errors ...") >> ../errors.txt
    echo  >> ../errors.txt
    (echo "BUILD ERRORS ...") >> ../errors.txt
    (grep 'ERROR' ../output.txt) >> ../errors.txt
    echo  >> ../errors.txt
    i=0
    while read line
    do 
        [ -z "$line" ] && break
        if [ $i -eq 0 ]
	then
	  (echo "TEST ERRORS ...") >> ../errors.txt
	fi
        echo "$line" >> ../errors.txt
        i=1
    done < <(grep -A10 'Tests run.*Failures.*Errors.* Skipped.*Time elapsed.*FAILURE' ../output.txt)
    echo  >> ../errors.txt
    echo "Active MQ Additional Testsuite was completed with errors ..."
else
    echo "Active MQ Additional Testsuite was completed successfully ..."
fi
