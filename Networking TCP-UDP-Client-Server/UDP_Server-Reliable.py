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
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server_socket.bind((localhost, server_port))
    print('The UDP server is ready to receive\n')
    while True:
        message, client_address = server_socket.recvfrom(2048)
        math_str = message.decode()
        sent_message = ''
        print('The message received at server is: ' + math_str + '\n')
        tokens = math_str.split(' ')
        valid_ops = ['+', '-', '*', '/']
        if len(tokens) != 3:
            sent_message = 'status code 530: invalid length'
            print('sending ' + sent_message)
            server_socket.sendto(sent_message.encode(), client_address)
            continue
        if tokens[0] not in valid_ops:
            sent_message = 'status code 520: invalid operator'
            print('sending ' + sent_message)
            server_socket.sendto(sent_message.encode(), client_address)
            continue
        elif not is_number(tokens[1]) or not is_number(tokens[2]):
            sent_message = 'status code 530: invalid operands'
            print('sending ' + sent_message)
            server_socket.sendto(sent_message.encode(), client_address)
            continue
        elif tokens[0] == '/' and float(tokens[2]) == 0:
            sent_message = 'status code 530: division by 0'
            print('sending ' + sent_message)
            server_socket.sendto(sent_message.encode(), client_address)
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
            server_socket.sendto(sent_message.encode(), client_address)

    


#########################

if __name__ == '__main__':
    main()
    
    
    
