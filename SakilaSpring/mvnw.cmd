@REM ---------------------------------------------------------------------------
@REM Maven Wrapper Startup Script (Windows)
@REM Cleaned, organized, and documented — functionally identical to stock
@REM Version: 3.3.4
@REM ---------------------------------------------------------------------------

@echo off
setlocal

REM Store script name
if "%__MVNW_ARG0_NAME__%"=="" (
    set "__MVNW_ARG0_NAME__=%~nx0"
)

REM Prepare temp variables
set "__MVNW_CMD__="
set "__MVNW_ERROR__="
set "__MVNW_PSMODULEP_SAVE=%PSModulePath%"
set "PSModulePath="

REM Execute PowerShell portion and capture its output
for /F "usebackq tokens=1* delims==" %%A in (`
  powershell -noprofile ^
    "& {
        $scriptDir = '%~dp0';
        $script    = '%__MVNW_ARG0_NAME__%';
        icm -ScriptBlock ([Scriptblock]::Create((Get-Content -Raw '%~f0'))) -NoNewScope
     }"
`) do (
    if "%%A"=="MVN_CMD" (
        set "__MVNW_CMD__=%%B"
    ) else if "%%B"=="" (
        echo %%A
    ) else (
        echo %%A=%%B
    )
)

REM Restore PS module path
set "PSModulePath=%__MVNW_PSMODULEP_SAVE%"
set "__MVNW_PSMODULEP_SAVE="

REM Clear wrapper temp vars
set "__MVNW_ARG0_NAME__="
set "MVNW_USERNAME="
set "MVNW_PASSWORD="

REM Execute the resolved Maven command
if not "%__MVNW_CMD__%"=="" (
    "%__MVNW_CMD__%" %*
    exit /b %errorlevel%
)

echo Cannot start Maven from wrapper >&2
exit /b 1

REM ---------------------------------------------------------------------------
REM END OF BATCH PORTION — POWERSHELL SECTION BEGINS BELOW
REM ---------------------------------------------------------------------------
#>

# ===========================
# PowerShell wrapper logic
# ===========================

$ErrorActionPreference = "Stop"

if ($env:MVNW_VERBOSE -eq "true") {
    $VerbosePreference = "Continue"
}

# --- Load wrapper properties ---
$propsPath = "$scriptDir/.mvn/wrapper/maven-wrapper.properties"
$props     = Get-Content -Raw $propsPath | ConvertFrom-StringData

$distributionUrl = $props.distributionUrl
if (-not $distributionUrl) {
    Write-Error "distributionUrl not found in $propsPath"
}

# Detect mvnd vs classic Maven
$basename = $distributionUrl -replace '^.*/',''
switch -wildcard -casesensitive ($basename) {
    "maven-mvnd-*" {
        $USE_MVND = $true
        $distributionUrl = $distributionUrl -replace '-bin\.[^.]*$',"-windows-amd64.zip"
        $MVN_CMD = "mvnd.cmd"
        break
    }
    default {
        $USE_MVND = $false
        $MVN_CMD  = $script -replace '^mvnw','mvn'
        break
    }
}

# Allow override repo URL
if ($env:MVNW_REPOURL) {
    $prefix = if ($USE_MVND) { "/maven/mvnd/" } else { "/org/apache/maven/" }
    $distributionUrl = "$env:MVNW_REPOURL$prefix$($distributionUrl -replace "^.*$prefix",'')"
}

# --- Build Maven home paths ---
$distName      = $distributionUrl -replace '^.*/',''
$distNameMain  = $distName -replace '\.[^.]*$','' -replace '-bin$',''

$M2 = $env:MAVEN_USER_HOME
if (-not $M2) { $M2 = "$HOME/.m2" }

$distRoot = "$M2/wrapper/dists/$distNameMain"

# Hash distribution URL → stable home folder
$hash   = ([System.Security.Cryptography.SHA256]::Create().ComputeHash([byte[]][char[]]$distributionUrl) |
           ForEach-Object { $_.ToString("x2") }) -join ''
$MAVEN_HOME = "$distRoot/$hash"

# Return existing installation
if (Test-Path $MAVEN_HOME) {
    Write-Output "MVN_CMD=$MAVEN_HOME/bin/$MVN_CMD"
    exit
}

# Validate distribution url format
if (!$distNameMain -or ($distName -eq $distNameMain)) {
    Write-Error "distributionUrl must end with *-bin.zip — got $distributionUrl"
}

# Create temp directory
$tmpHolder = New-TemporaryFile
$tmpDir    = New-Item -ItemType Directory -Path "$tmpHolder.dir"
$tmpHolder.Delete() | Out-Null

# Prepare target directory
New-Item -ItemType Directory -Force -Path $distRoot | Out-Null

# --- Download ---
Write-Verbose "Downloading Maven from $distributionUrl"
$zipFile = "$tmpDir/$distName"

$client = New-Object System.Net.WebClient
if ($env:MVNW_USERNAME -and $env:MVNW_PASSWORD) {
    $client.Credentials = New-Object System.Net.NetworkCredential($env:MVNW_USERNAME, $env:MVNW_PASSWORD)
}

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
$client.DownloadFile($distributionUrl, $zipFile)

# --- Optional SHA-256 verification ---
if ($props.distributionSha256Sum) {
    if ($USE_MVND) {
        Write-Error "mvnd does not support SHA-256 validation"
    }

    Import-Module Microsoft.PowerShell.Utility
    $actual = (Get-FileHash $zipFile -Algorithm SHA256).Hash.ToLower()

    if ($actual -ne $props.distributionSha256Sum) {
        Write-Error "SHA-256 checksum mismatch — Maven distribution may be corrupted."
    }
}

# --- Extract ---
Expand-Archive $zipFile -DestinationPath $tmpDir

# Locate actual extracted directory
$distributionDir = Get-ChildItem -Path $tmpDir -Directory |
    Where-Object { Test-Path "$($_.FullName)/bin/$MVN_CMD" } |
    Select-Object -First 1

if (-not $distributionDir) {
    Write-Error "Cannot locate extracted Maven distribution directory"
}

# Move installation into place
Rename-Item -Path $distributionDir.FullName -NewName $hash
Move-Item "$tmpDir/$hash" $distRoot -ErrorAction Stop

# Cleanup
Remove-Item $tmpDir -Recurse -Force

# Output final Maven command
Write-Output "MVN_CMD=$MAVEN_HOME/bin/$MVN_CMD"