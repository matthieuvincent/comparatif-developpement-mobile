async function computeSieveOfEratosthenes(limit: number): Promise<number> {
  if (limit < 2) return 0;

  const isPrime = new Uint8Array(limit + 1).fill(1);
  isPrime[0] = 0;
  isPrime[1] = 0;

  const sqrtLimit = Math.floor(Math.sqrt(limit));

  for (let i = 2; i <= sqrtLimit; i++) {
    if (!isPrime[i]) continue;

    for (let j = i * i; j <= limit; j += i) {
      isPrime[j] = 0;
    }

    // Libère le thread pour mise à jour UI
    await new Promise(resolve => setTimeout(resolve, 1));
  }

  let count = 0;
  for (let i = 2; i <= limit; i++) {
    count += isPrime[i];
  }

  return count;
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