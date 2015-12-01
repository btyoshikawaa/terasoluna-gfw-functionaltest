<?xml version='1.0' encoding='utf-8'?>
<!-- web application context file for tomcat. -->
<!-- this file should be place at CATALINA_HOME/conf/Catalina/localhost/terasoluna-gfw-functionaltest-web.xml -->
<Context>

  <Resource
    name="jdbc/gfwFunctionaltestDataSource"
    type="javax.sql.DataSource"
    driverClassName="oracle.jdbc.OracleDriver"
    username="gfw"
    password="gfw"
    url="jdbc:oracle:thin:@${HOST_IP!'localhost'}:${DBSRV_DB_PORT!'1521'}:teradb"
    maxIdle="16"
    minIdle="0"
    maxWait="60000"
    maxActive="96"/>

  <Resources className="org.apache.catalina.webresources.StandardRoot">
    <PreResources className="org.apache.catalina.webresources.DirResourceSet"
      base="/opt/tomcat/tomcat/webapps-env-jars/terasoluna-gfw-functionaltest-env-tomcat8-oracle/"
      internalPath="/"
      webAppMount="/WEB-INF/lib" />
  </Resources>
  <JarScanner scanAllDirectories="true" />

</Context>
