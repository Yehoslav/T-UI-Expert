# T-UI Expert

Telegram:
* [discussion group](https://t.me/t_ui_dev)
* [update channel](https://t.me/tui_expert_updates)

<img src="https://github.com/v1nc/TUI-Expert/raw/master/screenshots/1.png" data-canonical-src="https://gyazo.com/eb5c5741b6a9a16c692170a41a49c858.png" width="400" />



# [Download](https://github.com/v1nc/TUI-Expert/releases)
(dont expect stable until v1.0!)

# Fixes:
* hotfix for double(or more) notifications

## Fixes for root:
* make wifi cmd more reliable
* airplane mode works again
* tap to lock does not disable fingerprint anymore
# Features
### Root commands:
* `airplane`   toggle airplane mode
* `battery`    toggle battery saver mode
* `cellular`   toggle cellular connection
* `nfc`        toggle nfc
* `reboot`
* `recovery`   reboot straight into recovery
* `rotate`     toggle rotating screen
* `shutdown`   power off your phone
* `sleeptimer` after given minutes, your phone shuts down
### custom command output:
your command is executed periodically, the output is printed to the top of the terminal!
- `config -set custom_command wget -qO- wttr.in/your_city_name\?0T`
- `config -set custom_command_timeout 60`(refresh every 60s)

### kill command:
instantly close apps by, just type kill!

### More Settings:
* `numbering_notes` [true/false]: notes are numbered automatically
* `show_help` [true/false]: show/hide help button
* `show_call` [true/false]: show/hide call button
* `user_root` [true/false]: always use root if possible
