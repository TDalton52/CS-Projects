import socket
import sys

def is_number(string):
    try:
        int(string)
        return True
    except ValueError:
        return False

def main():
    print('server is up and running!')
    localhost = '127.0.0.1'
    server_port = 55555
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((localhost, server_port))
    server_socket.listen(1)
    print('The TCP server is ready to receive\n')
    while True:
        connected_socket, client_address = server_socket.accept()
        math_str = connected_socket.recv(2048).decode()
        sent_message = ''
        print('The message received at server is: ' + math_str + '\n')
        tokens = math_str.split(' ')
        valid_ops = ['+', '-', '*', '/']
        if len(tokens) != 3:
            sent_message = 'status code 530: invalid length'
            print('sending ' + sent_message)
            connected_socket.send(sent_message.encode())
            continue
        tokens[2].strip('\n\t\r')
        if tokens[0] not in valid_ops:
            sent_message = 'status code 520: invalid operator'
            print('sending ' + sent_message)
            connected_socket.send(sent_message.encode())
            continue
        elif not is_number(tokens[1]) or not is_number(tokens[2]):
            print('tokens[1] is ' + tokens[1] + ', which isnumeric is ' + str(tokens[1].isnumeric()))
            print('tokens[2] is ' + tokens[2] + ', which isnumeric is ' + str(tokens[2].isnumeric()))
            sent_message = 'status code 530: invalid operands'
            print('sending ' + sent_message)
            connected_socket.send(sent_message.encode())
            continue
        elif tokens[0] == '/' and float(tokens[2]) == 0:
            sent_message = 'status code 530: division by 0'
            print('sending ' + sent_message)
            connected_socket.send(sent_message.encode())
            continue
        else:
            num1 = float(tokens[1])
            num2 = float(tokens[2])
            if tokens[0] == '+':
                sent_message = 'status code 200: result is ' + str(float(tokens[1]) + float(tokens[2]))
            elif tokens[0] == '-':
                sent_message = 'status code 200: result is ' + str(float(tokens[1]) - float(tokens[2]))
            elif tokens[0] == '*':
                sent_message = 'status code 200: result is ' + str(float(tokens[1]) * float(tokens[2]))
            else:
                sent_message = 'status code 200: result is ' + str(float(tokens[1]) / float(tokens[2]))
            print('sending ' + sent_message)
            connected_socket.send(sent_message.encode())

    


#########################

if __name__ == '__main__':
    main()
