# RuM
RuM - Rule Mining platform

Initial documentation will not be public untill alpha version is ready

Issue tracking will not be used untill alpha version is ready

##Deployment info
Runs on virgo-tomcat-server-3.6.4.RELEASE

In file "java6-server.profile" add “JavaSE-1.7” under the section “org.osgi.framework.executionenvironment”

In file "serviceability.xml" add

```XML
<!-- RuM logging -->
	<logger level="INFO" additivity="false" name="ee.ut.cs.rum.virgoConsole">
		<appender-ref ref="EVENT_LOG_STDOUT" />
		<appender-ref ref="SIFTED_LOG_FILE" />
		<appender-ref ref="LOG_FILE" />
</logger>
```

Create a Postgesql 9.4 database named RuM (username/password 'postgres')