cd ..

call mvn clean

echo [starter-parent]
cd starter-parent
call mvn clean

echo [examples]
cd ..
cd examples
call mvn clean

echo [projects]
cd ..
cd projects
call mvn clean

rem 切换回运行目录
cd..
cd bin