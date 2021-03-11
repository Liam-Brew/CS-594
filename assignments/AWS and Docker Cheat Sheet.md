# AWS/Docker Cheat Sheet

## Table of Contents

- [AWS/Docker Cheat Sheet](#awsdocker-cheat-sheet)
  - [Table of Contents](#table-of-contents)
  - [AWS](#aws)
    - [SSH to EC2 Instance](#ssh-to-ec2-instance)
    - [SCP File to EC2 Instance](#scp-file-to-ec2-instance)
    - [Converting .pub to .pem](#converting-pub-to-pem)
  - [Docker](#docker)
    - [Exporting/Importing Docker Images](#exportingimporting-docker-images)

## AWS

### SSH to EC2 Instance

```sh
# Format
ssh -i [.pub_key_path] ec2-user@[IPv4DNS]

# Example
ssh -i ~/.ssh/aws.pub ec2-user@ec2-1-234-567-890.compute-1.amazonaws.com
```

### SCP File to EC2 Instance

```sh
# Format
scp -i [.pub_key_path] -r [path_to_file] ec2-user@[IPv4_DNS]:[target_directory]

# Example
scp -i ~/.ssh/aws.pub -r ~/.ssh/aws.pub ec2-user@ec2-1-234-567-890.compute-1.amazonaws.com:/home/ec2-user/.ssh
```

### Converting .pub to .pem

Keys in the .pub format may experience issues when used within the public subnet. I've found that converting them to the .pem format and adding appropriate file permissions seems to work well

```sh
cp ~/.ssh/aws.pub ~/.ssh/aws.pem

chmod 700 .ssh

chmod 600 .ssh/aws.pem
```

From here, make sure to use the .pem file when SSH-ing into the private subnet:

```sh
ssh -i ~/.ssh/aws.pem ec2-user@ip-12-3-4-567.ec2.internal
```

## Docker

### Exporting/Importing Docker Images

I use this to transfer work between my local Docker workstation and the cloud. The .tar archive is transported via the SCP method described above

```sh
# Export a Docker Image 
docker save -o [repository_name] [file_name].tar

# Import a Docker Image
docker load -i [file_name].tar
```
