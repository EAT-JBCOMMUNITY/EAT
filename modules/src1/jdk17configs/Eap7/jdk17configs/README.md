# EAT
-----
## THE JBOSS TESTSUITE TO DEVELOP TESTS AGAINST INFINITE NUMBER OF JBOSS SERVERS
--------------------------------------------------------------------------------
## A PROJECT UNDER THE ΙΔΕΑ STATEMENT
--------------------------------------


Testing EAP with jdk>14 (7.4.4)
--------------------------------------
In order to test eap from version 7.4.4 with jdk>14, the default sec subsystem should be removed and elytron should be used.
In addition, security-realm params of the various subsystems should be modified accordingly and tests relative to the removed sec subsystem should not be distributed.
Examples of such configs can be found in this dir. 




