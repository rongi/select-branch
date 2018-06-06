(ns select-branch.ui
  (:import (com.googlecode.lanterna.terminal DefaultTerminalFactory)
           (com.googlecode.lanterna.screen TerminalScreen)
           (com.googlecode.lanterna.gui2 Panel GridLayout Label BasicWindow MultiWindowTextGUI ActionListBox EmptyWindowDecorationRenderer)
           (com.googlecode.lanterna TerminalSize TextColor$ANSI SGR TextColor$RGB TextColor)
           (com.googlecode.lanterna.graphics SimpleTheme)))


(def ^:private ^TextColor white (TextColor$RGB. 0xFF 0xFF 0xFF))
(def ^:private ^TextColor black (TextColor$ANSI/BLACK))
(def ^:private ^TextColor selectedTextColor (TextColor$RGB. 0x42 0xB9 0x3D))

(defn create-lanterna-view []
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
        _ (.addWindowAndWait gui window)]
    (:list-box list-box)))