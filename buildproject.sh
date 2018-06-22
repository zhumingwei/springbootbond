##打包命令
./gradlew clean
#./gradlew bootJar
./gradlew build
export path=$PWD/build/libs/
file=$(ls $path)

scp -i ~/.ssh/server_id_rsa $path/$file root@198.181.39.114:/home/server/tomcat/apache-tomcat-8.5.31/webapps/bond.war

