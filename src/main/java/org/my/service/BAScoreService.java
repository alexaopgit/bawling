package org.my.service;

import org.springframework.stereotype.Service;

@Service
public class BAScoreService {
    private static int MAX_FRAMES_NUMBER = 10;
    private static int MAX_FRAME_SUM = 10;

    private void checkBADataCorrectness(int[] A) throws BAException {
        // array size has to be exactly 21
        if (A.length != 21) {
            throw new BAException("Array length isn't correct - " + A.length);
        }
        int frameSum = 0;
        int frameNumber = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] > MAX_FRAME_SUM) {
                throw new BAException("Element with index " + i + " has value bigger then 10");
            }
            if (i % 2 == 0) { // first ball
                frameNumber++;
                frameSum = A[i];
            } else { // second ball
                frameSum += A[i];
                if (frameSum > MAX_FRAME_SUM && frameNumber != MAX_FRAMES_NUMBER) {
                    throw new BAException("Sum for frame " + frameNumber + " has value bigger then 10");
                }
                if (frameNumber == MAX_FRAMES_NUMBER && frameSum > 2 * MAX_FRAME_SUM) {
                    throw new BAException("Sum for frame " + frameNumber + " has value bigger then 10");
                }
            }
        }
    }

    /**
     * Bowling game score counting
     * Functional design style
     *
     * @param A - knocked down pins by each ball
     * @return score for each frame
     */
    private int[] getBAScore(int[] A) {
        // sum in the current frame
        int frameSum = 0;
        // current frame number
        int frameNumber = 0;
        // strike sign
        boolean strike = false;
        // spare sign
        boolean spare = false;
        // score by frames
        int framesScore[] = new int[MAX_FRAMES_NUMBER];
        int prevFrameScore = 0;
        for (int i = 0; i < A.length; i++) {
            // check whether the input array is full
            if (A[i] == -1) {
                framesScore[frameNumber] = 0;
                break;
            }
            if (i % 2 == 0) { // first ball in the frame
                // check for strike
                if (A[i] == MAX_FRAME_SUM) {
                    strike = true;
                }
                frameSum = A[i];
            } else { // second ball in the frame
                frameSum += A[i];
                if (frameSum == MAX_FRAME_SUM && !strike) {
                    spare = true;
                }
                // sum of previous frame and current
                framesScore[frameNumber] = frameSum + prevFrameScore;
                if (spare || strike) {
                    // check whether the input array is full
                    if (A[i + 1] == -1) {
                        framesScore[frameNumber] = 0;
                        break;
                    }
                    // plus next shot
                    framesScore[frameNumber] += A[i + 1];
                }
                if (strike) {
                    // process all frames except the last one.
                    // for the last frame , frameSum contains first bonus shot and it was counted on the first step
                    if (frameNumber < MAX_FRAMES_NUMBER - 1) {
                        if (A[i + 1] == MAX_FRAME_SUM) { // next shot also a strike
                            // check whether the input array is full
                            if (A[i + 3] == -1) {
                                framesScore[frameNumber] = 0;
                                break;
                            }
                            // plus next next shot
                            framesScore[frameNumber] += A[i + 3];
                        } else {
                            // check whether the input array is full
                            if (A[i + 2] == -1) {
                                framesScore[frameNumber] = 0;
                                break;
                            }
                            // plus next next shot
                            framesScore[frameNumber] += A[i + 2];
                        }
                    }
                }
                // reset flags
                spare = false;
                strike = false;
                // keep current frame score for the next step
                prevFrameScore = framesScore[frameNumber];
                // increase frame counter
                frameNumber++;
            }
        }
        return framesScore;
    }

    /**
     * Get BA score
     *
     * @param knockedPins array of pins knocked down by each ball.
     *                    If it was a strike in a frame, then second ball knocked 0
     * @return score array for each frame
     * @throws BAException when array contains errors
     */
    public int[] runBAWithCheck(int[] knockedPins) throws BAException {
        checkBADataCorrectness(knockedPins);
        return getBAScore(knockedPins);
    }
}
