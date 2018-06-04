(ns select-branch.console
  (:import (com.googlecode.lanterna.terminal DefaultTerminalFactory)
           (com.googlecode.lanterna.screen TerminalScreen)
           (com.googlecode.lanterna.gui2 Panel GridLayout Label BasicWindow MultiWindowTextGUI ActionListBox)
           (com.googlecode.lanterna TerminalSize TextColor$ANSI SGR)
           (com.googlecode.lanterna.graphics SimpleTheme)))

(defn run-app []
  (let [terminal (.createTerminal (-> (DefaultTerminalFactory.)
                                      (.setForceTextTerminal false)))
        screen (TerminalScreen. terminal)
        _ (.startScreen screen)
        list-size (TerminalSize. 14 10)
        list-box (-> (ActionListBox. list-size)
                     (.addItem "Item1" (reify Runnable (run [_])))
                     (.addItem "Item2" (reify Runnable (run [_]))))
        panel (->
                (Panel.)
                (.setLayoutManager (GridLayout. 1))
                (.addComponent (Label. "Label"))
                (.addComponent list-box))
        window (BasicWindow.)
        _ (.setComponent window panel)
        gui (MultiWindowTextGUI. screen)
        _ (.setTheme gui (SimpleTheme.
                           (TextColor$ANSI/WHITE)
                           (TextColor$ANSI/BLACK)
                           (into-array SGR [])))
        ]
    (.addWindowAndWait gui window)))