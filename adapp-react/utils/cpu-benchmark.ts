async function computeSieveOfEratosthenes(limit: number): Promise<number> {
  if (limit < 2) return 0;

  const isPrime: boolean[] = new Array(limit + 1).fill(true);
  isPrime[0] = false;
  isPrime[1] = false;

  const sqrtLimit = Math.floor(Math.sqrt(limit));

  for (let i = 2; i <= sqrtLimit; i++) {
    if (!isPrime[i]) continue;

    for (let j = i * i; j <= limit; j += i) {
      isPrime[j] = false;
    }

    // Libère le thread pour mise à jour UI
    await new Promise(resolve => setTimeout(resolve, 1));
  }

  return isPrime.filter(Boolean).length;
}

export async function runSieveOfEratosthenes(limit: number, cycles: number): Promise<number> {
  let totalTime = 0;

  for (let i = 0; i < cycles; i++) {
    const start = performance.now();
    await computeSieveOfEratosthenes(limit);
    const end = performance.now();
    totalTime += end - start;
  }

  return totalTime / cycles;
}