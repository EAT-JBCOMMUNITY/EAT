#!/bin/bash

#set -e

checked_eat_prs=();
checked_eat_prs_uts=();

if [ -z "$SLEEP_TIME" ]; then
    export SLEEP_TIME=10
fi

c=0;
c2=0;

while true
do
        unset eat_arr;
eat_prs_get=$(curl -s -n https://api.github.com/repos/EAT-JBCOMMUNITY/EAT/pulls?state=open --header 'Authorization: token '$GITHUB_TOKEN);
eat_prs_number=$(echo $eat_prs_get | grep -Po '"number":.*?[^\\],');
eat_arr+=($(echo $eat_prs_number | grep -Po '[0-9]*')) ;
x1=$(grep -A 3  '"body":' <<< $eat_prs_get);
eat_prs_utime=();
       
        k=0;
        u=0;
for pr_num in "${eat_arr[@]}"
do      #Additional filters could be added here (e.g. pr,version filters for running at different AT servers)

       push_times=$(curl -s -n https://api.github.com/repos/EAT-JBCOMMUNITY/EAT/pulls/$pr_num/commits --header 'Authorization: token '$GITHUB_TOKEN);
       x1=$(grep -A 0  '"date":' <<< $push_times);
       x1=${x1##*--};
       eat_prs_utime[${#eat_prs_utime[@]}]=$x1

       if [ ${#checked_eat_prs[@]} -gt 0 ] && [ ${#checked_eat_prs[@]} -ge $k ]; then
   if [ "$pr_num" -gt "${checked_eat_prs[$k]}" ]; then
       u=$((u+1));
       c2=$((c2+1));
       c2=$((c2 % 32000));
       echo $(date) ... New pr : $pr_num
       docker run --rm --name atci_${pr_num}_${c2} -e TEST_PROGRAM=wildfly -e AT_PR=$pr_num -e GITHUB_TOKEN=$GITHUB_TOKEN -v $HOME/.m2/repository:/home/user/.m2/repository --privileged=true --ulimit nofile=5000:5000 docker.io/atci > output_$pr_num.txt &
   elif [ "$pr_num" -eq "${checked_eat_prs[$k]}" ]; then
       n=$((k+u));
       uts=$(echo ${eat_prs_utime[$n]}  | tr -cd [:digit:]);
       uts_ckecked=$(echo ${checked_eat_prs_uts[$k]}  | tr -cd [:digit:]);
       if [ "$uts" -gt "$uts_ckecked" ]; then
           echo $(date) ... Updated pr : $pr_num
           c2=$((c2+1));
           c2=$((c2 % 32000));
           docker run --rm --name atci_${pr_num}_${c2} -e TEST_PROGRAM=wildfly -e AT_PR=$pr_num -e GITHUB_TOKEN=$GITHUB_TOKEN -v $HOME/.m2/repository:/home/user/.m2/repository --privileged=true --ulimit nofile=5000:5000 docker.io/atci > output_$pr_num.txt &
       fi
       k=$((k+1));
       c=$((c+1));
   else
    break;
   fi
fi
done

checked_eat_prs=("${eat_arr[@]}");
checked_eat_prs_uts=("${eat_prs_utime[@]}");
eat_prs_utime=();

sleep $SLEEP_TIME

done
