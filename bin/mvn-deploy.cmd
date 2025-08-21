cd ..

call mvn clean deploy -Prelease

echo ################################
echo #  spring-boot-starter-parent  #
echo ################################
cd spring-boot-starter-parent
call mvn clean deploy -Prelease

rem 切换回运行目录
cd..
cd bin