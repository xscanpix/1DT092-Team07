import socket
import struct
import BasicFunctions as bs


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
        rec = self.sock.recv(2)  # Receive length of message
        length = int.from_bytes(rec, byteorder='little')
        rec = self.sock.recv(length)  # Receive message
        return rec


def main():
    comm = Communication()
    comm.connect('127.0.0.1', 5555)

    msg = comm.myreceive()

    op = int.from_bytes(msg[:2], byteorder='big')

    if(op == 0):  # Setup message
        id = int.from_bytes(msg[2:], byteorder='big')
        r = Robot(comm, id, 1, 1)
        msg = struct.pack('hhhh', 1, r.id, r.x, r.y)
        comm.mysend(msg)
    elif(op == 2):  # Move message
        print(1)


if __name__ == "__main__":
    main()
