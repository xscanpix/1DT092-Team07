
import time
import BasicFunctions as fct
import curses


def stop():
    fct.stop(9)

def react(c):
    if c in [ord('q'), 27, 32]:
        stop()
        return
    elif c == curses.KEY_LEFT:
        fct.goFullSpeedLeft()
    elif c == curses.KEY_RIGHT:
        fct.goFullSpeedRight()
    elif c == curses.KEY_UP:
        fct.goForward()
    elif c == curses.KEY_DOWN:
        fct.goBackward()

def main(window):
    stdscr = window
    stdscr.clear()      # print introduction
    stdscr.refresh()
    stdscr.addstr(0, 0, 'Use Arrows to navigate EV3-vehicle')
    stdscr.addstr(1, 0, 'Pause vehicle with key <SPACE>')
    stdscr.addstr(2, 0, 'Exit with key <q>')

    while True:
        c = stdscr.getch()
        print(c)
        if c in [ord('q'), 27]:
            react(c)
            break
        elif c in [32,
                   curses.KEY_RIGHT, curses.KEY_LEFT, curses.KEY_UP, curses.KEY_DOWN]:
            react(c)

curses.wrapper(main)
