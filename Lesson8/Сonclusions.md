В качестве критерия берем среднее время задержки процессора (время выполнения сборки)  

Измеренные результаты для -Xms256m -Xmx256m

G1:  
G1 Young Generation---  Min duration: 3ms, Max duration: 34ms, Avg duration: 10.233333333333333ms  
G1 Old Generation---    Min duration: 149ms, Max duration: 179ms, Avg duration: 156.33333333333334ms  

SerialGC:  
Copy---                 Min duration: 55ms, Max duration: 180ms, Avg duration: 84.8ms      
MarkSweepCompact---     Min duration: 262ms, Max duration: 364ms, Avg duration: 292.3888888888889ms  

Parallel Collector:  
PS Scavenge---          Min duration: 24ms, Max duration: 65ms, Avg duration: 40.0ms        
PS MarkSweep---         Min duration: 264ms, Max duration: 350ms, Avg duration: 296.9047619047619ms

Измеренные результаты для -Xms4096m -Xmx4096m    

G1:  
G1 Young Generation---  Min duration: 13ms, Max duration: 1221ms, Avg duration: 189.82222222222222ms  
G1 Old Generation---    Min duration: 2388ms, Max duration: 12098ms, Avg duration: 3889.909090909091ms  

SerialGC:  
Copy---                 Min duration: 725ms, Max duration: 1124ms, Avg duration: 896.2ms  
MarkSweepCompact---     Min duration: 4357ms, Max duration: 5274ms, Avg duration: 4755.333333333333ms  

Parallel Collector:  
PS Scavenge---          Min duration: 425ms, Max duration: 1696ms, Avg duration: 836.5ms
PS MarkSweep---         Min duration: 272ms, Max duration: 7001ms, Avg duration: 4671.427983539094ms  

Исходя из полученных данных видно, что наименьшее время задержки (быстрее выполняет работу) сборщик мусора GC1. 
Делаем выбор в пользу него. 