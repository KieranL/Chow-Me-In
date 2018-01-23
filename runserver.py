#!/usr/bin/python3

import subprocess

def main():
	# Make sure requirements are up to date prior to launching server
	subprocess.run(['sudo', '-H', 'pip3', 'install', '-r', 'server/requirements.txt'])

	# Run Flask server
	subprocess.run(['python3', 'server/server.py'])


if __name__ == '__main__':
	main()