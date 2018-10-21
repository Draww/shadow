/*
 * This file is part of shadow, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package me.lucko.shadow;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Defines a class, method or field target with a constant, known value.
 */
@java.lang.annotation.Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Target {

    /**
     * Gets the value.
     *
     * @return the value
     */
    @NonNull String value();

    /**
     * A {@link TargetResolver} for the {@link Target} annotation.
     */
    TargetResolver RESOLVER = new TargetResolver() {
        @Override
        public @NonNull Optional<Class<?>> lookupClass(@NonNull Class<? extends Shadow> shadowClass) throws ClassNotFoundException {
            Target target = shadowClass.getAnnotation(Target.class);
            if (target == null) {
                return Optional.empty();
            }

            return Optional.of(Class.forName(target.value()));
        }

        @Override
        public @NonNull Optional<String> lookupMethod(@NonNull Method shadowMethod, @NonNull Class<? extends Shadow> shadowClass, @NonNull Class<?> targetClass) {
            return Optional.ofNullable(shadowMethod.getAnnotation(Target.class)).map(Target::value);
        }

        @Override
        public @NonNull Optional<String> lookupField(@NonNull Method shadowMethod, @NonNull Class<? extends Shadow> shadowClass, @NonNull Class<?> targetClass) {
            return Optional.ofNullable(shadowMethod.getAnnotation(Target.class)).map(Target::value);
        }
    };

}
