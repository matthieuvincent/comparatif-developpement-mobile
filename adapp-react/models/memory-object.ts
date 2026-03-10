export class MemoryObject {
    public readonly payload: Uint8Array;

    constructor(public readonly payloadSize: number) {
        this.payload = new Uint8Array(payloadSize);
    }
}

export interface MemoryTestResult {
    growthRate: number,
    volatility: number,
    intercept: number,
    memoryAverageElapsedTimePerCycle: number
}