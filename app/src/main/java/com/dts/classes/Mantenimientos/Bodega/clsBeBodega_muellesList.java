package com.dts.classes.Mantenimientos.Bodega;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeBodega_muellesList {
    @ElementList(inline=true,required=false)
    public List<clsBeBodega_muelles> items = new List<clsBeBodega_muelles>() {
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
        public Iterator<clsBeBodega_muelles> iterator() {
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
        public boolean add(clsBeBodega_muelles clsBeBodega_muelles) {
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
        public boolean addAll(@NonNull Collection<? extends clsBeBodega_muelles> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeBodega_muelles> c) {
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
        public clsBeBodega_muelles get(int index) {
            return null;
        }

        @Override
        public clsBeBodega_muelles set(int index, clsBeBodega_muelles element) {
            return null;
        }

        @Override
        public void add(int index, clsBeBodega_muelles element) {

        }

        @Override
        public clsBeBodega_muelles remove(int index) {
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
        public ListIterator<clsBeBodega_muelles> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeBodega_muelles> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeBodega_muelles> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}