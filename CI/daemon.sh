#!/bin/bash

#set -e

checked_eat_prs=();
checked_eat_prs_uts=();

if [ -z "$SLEEP_TIME" ]; then
    export SLEEP_TIME=60
fi

while true
do
        unset eat_arr;
	eat_prs_get=$(curl -s -n https://api.github.com/repos/EAT-JBCOMMUNITY/EAT/pulls?state=open --header 'Authorization: token '$GITHUB_TOKEN);
	eat_prs_number=$(echo $eat_prs_get | grep -Po '"number":.*?[^\\],');
	eat_arr+=($(echo $eat_prs_number | grep -Po '[0-9]*')) ;
	x1=$(grep -A 3  '"body":' <<< $eat_prs_get);
	eat_prs_utime_temp=$(echo $x1 | grep -Po '"updated_at":.*?[^\\],');
	eat_prs_utime_temp=${eat_prs_utime_temp//[[:blank:]]/};
        eat_prs_utime=(${eat_prs_utime_temp//,/ })
        
        k=0;
	for pr_num in "${eat_arr[@]}"
	do
	        if [ ${#checked_eat_prs[@]} -gt 0 ] && [ ${#checked_eat_prs[@]} -ge $k ]; then
		    if [ "$pr_num" -gt "${checked_eat_prs[$k]}" ]; then
		        echo $(date) ... New pr : $pr_num 
		    elif [ "$pr_num" -eq "${checked_eat_prs[$k]}" ]; then
		        uts=$(echo ${eat_prs_utime[$k]}  | tr -cd [:digit:]);
		        uts_ckecked=$(echo ${checked_eat_prs_uts[$k]}  | tr -cd [:digit:]);
		        if [ "$uts" -gt "$uts_ckecked" ]; then
		            echo $(date) ... Updated pr : $pr_num 
		        fi
		        k=$((k+1));
		    else
		    	break;
		    fi
		fi
	done
	
	checked_eat_prs=("${eat_arr[@]}");
	checked_eat_prs_uts=("${eat_prs_utime[@]}");
	
	sleep $SLEEP_TIME
	
done
