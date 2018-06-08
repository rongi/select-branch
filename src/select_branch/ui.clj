(ns select-branch.ui
  (:import (com.googlecode.lanterna.terminal DefaultTerminalFactory)
           (com.googlecode.lanterna.screen TerminalScreen)
           (com.googlecode.lanterna.gui2 Panel GridLayout Label BasicWindow Window$Hint MultiWindowTextGUI ActionListBox EmptyWindowDecorationRenderer MultiWindowTextGUI TextGUIThread)
           (com.googlecode.lanterna TerminalSize TextColor$ANSI TextColor$RGB TextColor)
           (com.googlecode.lanterna.graphics SimpleTheme)))


(def ^:private ^TextColor white (TextColor$RGB. 0xFF 0xFF 0xFF))
(def ^:private ^TextColor black (TextColor$ANSI/BLACK))
(def ^:private ^TextColor selectedTextColor (TextColor$RGB. 0x42 0xB9 0x3D))

(defn start-gui [on-gui-created]
  (let [terminal (.createTerminal (-> (DefaultTerminalFactory.)
                                      (.setForceTextTerminal false)))
        screen (TerminalScreen. terminal)
        _ (.startScreen screen)
        list-size (TerminalSize. 14 10)
        list-box (ActionListBox. list-size)
        panel (->
                (Panel.)
                (.setLayoutManager (GridLayout. 1))
                (.addComponent (Label. "Label"))
                (.addComponent list-box))
        window (BasicWindow.)
        _ (.setHints window (list Window$Hint/NO_DECORATIONS Window$Hint/NO_POST_RENDERING))
        _ (.setComponent window panel)

        gui (MultiWindowTextGUI. screen white)
        theme (-> (SimpleTheme/makeTheme
                    true
                    black
                    white
                    black
                    white
                    black
                    selectedTextColor
                    black)
                  (.setWindowDecorationRenderer (EmptyWindowDecorationRenderer.))
                  (.setWindowPostRenderer nil))
        _ (.setTheme gui theme)
        _ (on-gui-created {:list-box list-box})
        _ (.addWindowAndWait gui window)]))