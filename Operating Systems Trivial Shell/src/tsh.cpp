#include <tsh.h>
#include <sys/wait.h>
#include <string.h>
#include <iostream>

using namespace std;

void simple_shell::parse_command(char* cmd, char** cmdTokens)
{
    // TODO: tokenize the command string into arguments
  char* token;
  int index = 0;
  token = strtok(cmd, " \n\r\t");
  while(token != NULL)
  {
    cmdTokens[index] = token;
    token = strtok(NULL, " \n\r\t");
    index++;
  }
  cmdTokens[index] = NULL;
}

void simple_shell::exec_command(char** argv)
{
    // TODO: fork a child process to execute the command.
    // parent process should wait for the child process to complete and reap it
  pid_t pid = fork();
  int status;
  if(pid < 0)
  {
    fprintf(stderr, "fork system call failed!\n");
    exit(1);
  }
  else if(pid == 0) //we are in the child process, executing command
  {
    if(execvp(*argv, argv) < 0) //execvp only returns if unsucessful
    {
      fprintf(stderr, "execvp failed! errno is [%d]\n", errno);
      exit(1);
    }
  }
  else //we are in the parent process, waiting on child
  {
    while(wait(&status) != pid);
  }
}

bool simple_shell::isQuit(char* cmd)
{
  return strcmp(cmd, "quit") == 0;
}
