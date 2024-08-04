with import <nixpkgs> { config.allowUnfree = true; };
  pkgs.mkShell {
    buildInputs = [
      android-studio
      gradle_6
      openjdk17-bootstrap 
      # openjdk8-bootstrap 
      java-language-server
    ];
  }
