let
  unstable = import (fetchTarball "https://nixos.org/channels/nixos-unstable/nixexprs.tar.xz") { config.allowUnfree = true;  };
in
with import <nixpkgs> { config.allowUnfree = true; };
  pkgs.mkShell {
    buildInputs = [
      # android-studio
      unstable.androidStudioPackages.canary

      unstable.gradle
      # openjdk17-bootstrap 
      # java-language-server

    ];
  }
