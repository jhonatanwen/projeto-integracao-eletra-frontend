#define MyAppName "ProjetoIntegracao"
#define MyAppIco "ProjetoIntegracao.ico"
#define MyAppWizardIconBmp "ProjetoIntegracao-setup-icon.bmp"
#define MyAppVersion "0.3.0"
#define MyAppPublisher "Jhonatan"
#define MyAppExeName "ProjetoIntegracao.exe"
#define MyAppId "UPDATE-DETECTION"

; Detecta automaticamente se esta rodando via javapackager ou direto da pasta
#if FileExists(AddBackslash(SourcePath) + "ProjetoIntegracao\ProjetoIntegracao.exe")
  #define AppBundleDir "ProjetoIntegracao"
  #define OutputFolder "."
#else
  #define AppBundleDir "..\..\..\..\..\target\jfx\native\ProjetoIntegracao"
  #define OutputFolder "..\..\..\..\..\target\jfx\native"
#endif

[Setup]
AppId={#MyAppId}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppCopyright=Copyright (C) 2026
DefaultDirName={pf}\{#MyAppName}
DefaultGroupName={#MyAppName}
DisableStartupPrompt=Yes
DisableDirPage=No
DisableProgramGroupPage=Yes
DisableReadyPage=No
DisableFinishedPage=No
DisableWelcomePage=No
MinVersion=0,6.1
OutputDir={#OutputFolder}
OutputBaseFilename={#MyAppName}-{#MyAppVersion}-setup
Compression=lzma
SolidCompression=yes
PrivilegesRequired=admin
UninstallDisplayName={#MyAppName}
UninstallDisplayIcon={app}\{#MyAppExeName}
WizardImageFile={#MyAppWizardIconBmp}
WizardSmallImageFile={#MyAppWizardIconBmp}
ArchitecturesInstallIn64BitMode=x64
SetupIconFile={#AppBundleDir}\{#MyAppIco}

[Languages]
Name: "brazilianportuguese"; MessagesFile: "compiler:Languages\BrazilianPortuguese.isl"
Name: "english"; MessagesFile: "compiler:Default.isl"

[CustomMessages]
brazilianportuguese.CreateStartMenuIcon=Criar um atalho no Menu Iniciar
english.CreateStartMenuIcon=Create a Start Menu shortcut

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked
Name: "startmenuicon"; Description: "{cm:CreateStartMenuIcon}"; GroupDescription: "{cm:AdditionalIcons}"

[Files]
Source: "{#AppBundleDir}\{#MyAppExeName}"; DestDir: "{app}"; Flags: ignoreversion
Source: "{#AppBundleDir}\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
; Cria o atalho no Menu Iniciar se a opcao "startmenuicon" estiver marcada
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; WorkingDir: "{app}"; Tasks: startmenuicon
; Cria o atalho na Area de Trabalho se a opcao "desktopicon" estiver marcada
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; WorkingDir: "{app}"; Tasks: desktopicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
  Result := True;
end;