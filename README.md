# Clojure Tutorial

**Requirements**: JDK (Java Development Kit), [Clojure](https://clojure).

With [Visual Studio Code](https://code.visualstudio.com/) and [Calva](https://marketplace.visualstudio.com/items?itemName=betterthantomorrow.calva):

1. Open `src/backend/server.clj`.
2. Use the Command Palette (`Ctrl+Shift+P`) to run _Calva: Start a Project REPL and Connect_ (shortcut: `Ctrl+Alt+C, Ctrl+Alt+J`). Choose `deps.edn` as the project type and `:dev` as the alias.
3. Use the Command Palette (`Ctrl+Shift+P`) to run _Calva: Load/Evaluate Current File and its Requires/Dependencies_ (shortcut:  `Ctrl+Alt+C, Enter`).
4. To evaluate a form, use the Command Palette (`Ctrl+Shift+P`) to run _Calva: Evaluate Current Form_ (shortcut:  `Ctrl+Enter`).

With [Vim](https://www.vim.org/)/[NeoVim](https://neovim.io/) and [vim-iced plugin](https://github.com/liquidz/vim-iced):

1. Open `src/backend/server.clj`.
2. Run `:IcedJackIn` and wait until it is ready (with the message `Connected` indicating a successful jack-in and REPL connection).
3. Evaluate a form with `<Leader>ee`.
4. Show inline documentation with `K`.
5. Show the signature with `Shift+K`.
6. Toggle stdout buffer with `<Leader>ss`.