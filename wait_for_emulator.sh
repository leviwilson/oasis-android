until [ "`adb devices | grep "emulator-[0-9][0-9]*[[:space:]]*device"`" ]
do
  echo "waiting for emulator..."
  sleep 10
done
echo "emulator ready.  waiting a couple more minutes because emulators are fickle beasts..."
sleep 120
echo "Ready to roll"
