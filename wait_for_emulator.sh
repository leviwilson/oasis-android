until [ "`adb devices | grep "emulator-[0-9][0-9]*[[:space:]]*device"`" ]
do
  echo "waiting for emulator..."
  sleep 10
done
echo "Ready to roll"
