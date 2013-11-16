rm -f obj.txt le.txt infl.txt
sort <obj.txt.unsorted >obj.txt.sorted
sort <le.txt.unsorted >le.txt.sorted
sort <infl.txt.unsorted >infl.txt.sorted
cat COPYRIGHT obj.txt.sorted >obj.txt
cat COPYRIGHT le.txt.sorted >le.txt
cat COPYRIGHT infl.txt.sorted >infl.txt
rm -f obj.txt.unsorted le.txt.unsorted infl.txt.unsorted
rm -f obj.txt.sorted le.txt.sorted infl.txt.sorted
ls -l *.txt
wc -l *.txt
