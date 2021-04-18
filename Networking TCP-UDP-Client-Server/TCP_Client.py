import sys
import socket

def main():
    print('client is up and running!')
    localhost = '127.0.0.1'
    server_port = 55555
    if len(sys.argv) != 2:
        print('invalid number of arguments')
        sys.exit()
    file = open(sys.argv[1])
    contents = file.readlines()
    for i in range (len(contents)):
        client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        client_socket.connect((localhost, server_port))
        client_socket.send(contents[i].encode())
        message = client_socket.recv(2048)
        print('received ' + message.decode() + ' at client')
        client_socket.close()


#########################

if __name__ == '__main__':
    main()
