@echo off
echo ========================================
echo    AUTOWAREHOUSE DEVELOPMENT SERVER
echo ========================================
echo.
echo Starting Frontend and Backend servers...
echo.

REM Check if Node.js is installed
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Node.js is not installed or not in PATH
    echo Please install Node.js from https://nodejs.org/
    pause
    exit /b 1
)

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Apache Maven
    pause
    exit /b 1
)

echo All prerequisites are installed!
echo.

REM Start Backend in new window
echo Starting Backend Server (Quarkus)...
start "Autowarehouse Backend" cmd /k "cd autowarehouse-backend && mvn quarkus:dev"

REM Wait a bit for backend to start
timeout /t 5 /nobreak >nul

REM Start Frontend in new window
echo Starting Frontend Server (Vite)...
start "Autowarehouse Frontend" cmd /k "cd autowarehouse-frontend && npm run dev"

echo.
echo ========================================
echo   SERVERS STARTING...
echo ========================================
echo.
echo Backend:  http://localhost:8080
echo Frontend: http://localhost:5173
echo API Docs: http://localhost:8080/q/swagger-ui
echo.
echo Press any key to open the application in browser...
pause >nul

REM Open browser
start http://localhost:5173

echo.
echo Both servers are running in separate windows.
echo Close those windows to stop the servers.
echo.
pause
