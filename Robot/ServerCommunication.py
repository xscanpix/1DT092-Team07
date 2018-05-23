import socket
import struct
import BasicFunctions as bf
import FollowLines as fl


class Robot:
    def __init__(self, comm, id, x, y):
        self.comm = comm
        self.id = id
        self.x = x
        self.y = y


class Communication:
    def __init__(self, sock=None):
        if sock is None:
            self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        else:
            self.sock = sock

    def connect(self, host, port):
        self.sock.connect((host, port))

    def mysend(self, msg):
        totalsent = 0
        self.sock.send(struct.pack('h', len(msg)))
        while totalsent < len(msg):
            sent = self.sock.send(msg[totalsent:])
            if sent == 0:
                raise RuntimeError("socket connection broken")
            totalsent = totalsent + sent

    def myreceive(self):

        rec = self.sock.recv(1)
        l = int.from_bytes(rec, byteorder='little')
    
        m = b''
        while len(m) < l:
            rec = self.sock.recv(l - len(m))
            m = m + rec
        return rec


def main():
    comm = Communication()
    comm.connect('127.0.0.1', 5555)

    while(True):
        msg = comm.myreceive()
        print('Received message: ' + str(msg))

        op = int.from_bytes(msg[:2], byteorder='big')
        id = int.from_bytes(msg[2:4], byteorder='big')
    
        if(op == 0):  # Setup message
            r = Robot(comm, id, 1, 1)
            msg = struct.pack('hhhh', 1, r.id, r.x, r.y)
            comm.mysend(msg)
        elif(op == 2):  # Move message
            dir = int.from_bytes(msg[4:6], byteorder='big')
            result = False
            if(dir == 1):
                bf.goForward()
            elif(dir == 2):
                bf.goBackward()
            elif(dir == 3):
                bf.turn90Right()
            elif(dir == 4):
                bf.turn90Left()
            elif(dir == 5):
                result = fl.followLine(2)
            elif(dir == 6):
                fl.turnRight()
            elif(dir == 7):
                fl.turnLeft()
            elif(dir == 8):
                bf.turn180Right()
            elif(dir == 9):
                fl.goForwardIntersection()
            elif(dir == 10):
                bf.trolleyUp()
            elif(dir == 11):
                bf.trolleyDown()
            elif(dir == 12):
                fl.takeLoads()
            elif(dir == 13):
                fl.leaveLoad()
            elif(dir == 14):
                bf.stop()

            if(result == True):
                comm.mysend(struct.pack('hhh', 3, r.id, 0))

if __name__ == "__main__":
    main()
