package com.dts.classes.Transacciones.Recepcion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeTarea_hhList {
    @ElementList(inline=true,required = false)
    public List<clsBeTarea_hh> items = new List<clsBeTarea_hh>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeTarea_hh> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeTarea_hh clsBeTarea_hh) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeTarea_hh> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeTarea_hh> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeTarea_hh get(int index) {
            return null;
        }

        @Override
        public clsBeTarea_hh set(int index, clsBeTarea_hh element) {
            return null;
        }

        @Override
        public void add(int index, clsBeTarea_hh element) {

        }

        @Override
        public clsBeTarea_hh remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeTarea_hh> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeTarea_hh> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeTarea_hh> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}